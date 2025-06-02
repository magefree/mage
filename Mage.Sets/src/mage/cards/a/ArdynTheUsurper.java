package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdynTheUsurper extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DEMON, "Demons");

    public ArdynTheUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Demons you control have menace, lifelink, and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText(", and haste"));
        this.addAbility(ability);

        // Starscourge -- At the beginning of combat on your turn, exile up to one target creature card from a graveyard. If you exiled a card this way, create a token that's a copy of that card, except it's a 5/5 black Demon.
        ability = new BeginningOfCombatTriggeredAbility(new ArdynTheUsurperEffect());
        ability.addTarget(new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD
        ));
        this.addAbility(ability.withFlavorWord("Starscourge"));
    }

    private ArdynTheUsurper(final ArdynTheUsurper card) {
        super(card);
    }

    @Override
    public ArdynTheUsurper copy() {
        return new ArdynTheUsurper(this);
    }
}

class ArdynTheUsurperEffect extends OneShotEffect {

    ArdynTheUsurperEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target creature card from a graveyard. If you exiled a card this way, " +
                "create a token that's a copy of that card, except it's a 5/5 black Demon";
    }

    private ArdynTheUsurperEffect(final ArdynTheUsurperEffect effect) {
        super(effect);
    }

    @Override
    public ArdynTheUsurperEffect copy() {
        return new ArdynTheUsurperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect(
                null, null, false, 1, false,
                false, null, 5, 5, false
        ).setOnlyColor(ObjectColor.BLACK)
                .setOnlySubType(SubType.DEMON)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
    }
}
