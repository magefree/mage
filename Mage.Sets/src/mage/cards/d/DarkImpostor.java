package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public final class DarkImpostor extends CardImpl {

    public DarkImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{B}{B}: Exile target creature and put a +1/+1 counter on Dark Impostor.
        Ability ability = new SimpleActivatedAbility(
                new DarkImpostorExileTargetEffect(), new ManaCostsImpl<>("{4}{B}{B}")
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("and put a +1/+1 counter on {this}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Dark Impostor has all activated abilities of all creature cards exiled with it.
        this.addAbility(new SimpleStaticAbility(new DarkImpostorContinuousEffect()));
    }

    private DarkImpostor(final DarkImpostor card) {
        super(card);
    }

    @Override
    public DarkImpostor copy() {
        return new DarkImpostor(this);
    }
}

class DarkImpostorExileTargetEffect extends OneShotEffect {

    DarkImpostorExileTargetEffect() {
        super(Outcome.Exile);
        staticText = "exile target creature";
    }

    private DarkImpostorExileTargetEffect(final DarkImpostorExileTargetEffect effect) {
        super(effect);
    }

    @Override
    public DarkImpostorExileTargetEffect copy() {
        return new DarkImpostorExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        return player.moveCardsToExile(
                permanent, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
    }
}

class DarkImpostorContinuousEffect extends ContinuousEffectImpl {

    DarkImpostorContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creature cards exiled with it";
    }

    private DarkImpostorContinuousEffect(final DarkImpostorContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (permanent == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        for (Card card : exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability instanceof ActivatedAbility) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

    @Override
    public DarkImpostorContinuousEffect copy() {
        return new DarkImpostorContinuousEffect(this);
    }
}
