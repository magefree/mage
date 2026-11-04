package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class LokiTheDeceiver extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VILLAIN, "another target Villain you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.VILLAIN, "Villains");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public LokiTheDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Loki attacks, create a tapped and attacking token that's a copy of another target Villain you control, except it isn't legendary and it's an Illusion in addition to its other types. Sacrifice that token at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new LokiTheDeceiverEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever one or more Villains you control deal combat damage to a player, draw a card.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter2));
    }

    private LokiTheDeceiver(final LokiTheDeceiver card) {
        super(card);
    }

    @Override
    public LokiTheDeceiver copy() {
        return new LokiTheDeceiver(this);
    }
}

class LokiTheDeceiverEffect extends OneShotEffect {

    LokiTheDeceiverEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of another target Villain you control, " +
                "except it isn't legendary and it's an Illusion in addition to its other types. " +
                "Sacrifice that token at the beginning of the next end step";
    }

    private LokiTheDeceiverEffect(final LokiTheDeceiverEffect effect) {
        super(effect);
    }

    @Override
    public LokiTheDeceiverEffect copy() {
        return new LokiTheDeceiverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, true
        );
        effect.setIsntLegendary(true);
        effect.withAdditionalSubType(SubType.ILLUSION);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
