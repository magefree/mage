package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishraLostToPhyrexia extends MeldCard {

    private static final FilterPermanent filter = new FilterPermanent("artifact or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public MishraLostToPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);
        this.color.setBlack(true);
        this.color.setRed(true);

        // Whenever Mishra, Lost to Phyrexia enters the battlefield or attacks, choose three --
        // * Target opponent discards two cards.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        ability.getModes().setMinModes(3);
        ability.getModes().setMaxModes(3);

        // * Mishra deals 3 damage to any target.
        ability.addMode(new Mode(new DamageTargetEffect(3)).addTarget(new TargetAnyTarget()));

        // * Destroy target artifact or planeswalker.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));

        // * Creatures you control gain menace and trample until end of turn.
        ability.addMode(new Mode(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("creatures you control gain menace")).addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and trample until end of turn")));

        // * Creatures you don't control get -1/-1 until end of turn.
        ability.addMode(new Mode(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false
        )));

        // * Create two tapped Powerstone tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new PowerstoneToken(), 2, true, false)));

        this.addAbility(ability);
    }

    private MishraLostToPhyrexia(final MishraLostToPhyrexia card) {
        super(card);
    }

    @Override
    public MishraLostToPhyrexia copy() {
        return new MishraLostToPhyrexia(this);
    }
}
