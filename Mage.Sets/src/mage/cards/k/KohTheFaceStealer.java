package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KohTheFaceStealer extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another nontoken creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public KohTheFaceStealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Koh enters, exile up to one other target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Whenever another nontoken creature dies, you may exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ExileTargetForSourceEffect().withTargetDescription("it"), true, filter, true
        ));

        // Pay 1 life: Choose a creature card exiled with Koh.
        this.addAbility(new SimpleActivatedAbility(new KohTheFaceStealerChooseEffect(), new PayLifeCost(1)));

        // Koh has all activated and triggered abilities of the last chosen card.
        this.addAbility(new SimpleStaticAbility(new KohTheFaceStealerAbilityEffect()));
    }

    private KohTheFaceStealer(final KohTheFaceStealer card) {
        super(card);
    }

    @Override
    public KohTheFaceStealer copy() {
        return new KohTheFaceStealer(this);
    }
}

class KohTheFaceStealerChooseEffect extends OneShotEffect {

    KohTheFaceStealerChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature card exiled with {this}";
    }

    private KohTheFaceStealerChooseEffect(final KohTheFaceStealerChooseEffect effect) {
        super(effect);
    }

    @Override
    public KohTheFaceStealerChooseEffect copy() {
        return new KohTheFaceStealerChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        if (Optional
                .ofNullable(game.getExile().getExileZone(exileId))
                .filter(HashSet::isEmpty)
                .isPresent()) {
            return false;
        }
        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileId);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.getState().setValue(
                CardUtil.getObjectZoneString(
                        "chosenCard", permanent.getId(), game,
                        permanent.getZoneChangeCounter(game), false
                ),
                new MageObjectReference(card, game)
        );
        permanent.addInfo("chosen card", CardUtil.addToolTipMarkTags("Chosen Card " + card.getIdName()), game);
        return true;
    }
}

class KohTheFaceStealerAbilityEffect extends ContinuousEffectImpl {

    KohTheFaceStealerAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} has all activated and triggered abilities of the last chosen card";
    }

    private KohTheFaceStealerAbilityEffect(final KohTheFaceStealerAbilityEffect effect) {
        super(effect);
    }

    @Override
    public KohTheFaceStealerAbilityEffect copy() {
        return new KohTheFaceStealerAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Card card = Optional
                .ofNullable(CardUtil.getCardZoneString("chosenCard", source.getSourceId(), game, false))
                .map(game.getState()::getValue)
                .map(MageObjectReference.class::cast)
                .map(mor -> mor.getCard(game))
                .orElse(null);
        if (permanent == null || card == null) {
            return false;
        }
        for (Ability ability : card.getAbilities(game)) {
            if (ability.isActivatedAbility() || ability.isTriggeredAbility()) {
                permanent.addAbility(ability, source.getSourceId(), game, true);
            }
        }
        return true;
    }
}
