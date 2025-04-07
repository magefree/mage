package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WarriorToken;

import mage.abilities.costs.common.TapSourceCost;

import java.util.UUID;

/**
 * Dalkovan Encampment implementation
 * Author: @mikejcunn
 */
public final class DalkovanEncampment extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a Swamp or a Mountain");

    static {
        filter.add(Predicates.or(SubType.SWAMP.getPredicate(), SubType.MOUNTAIN.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public DalkovanEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Dalkovan Encampment enters the battlefield tapped unless you control a Swamp or a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {2}{W}, {T}: Whenever you attack this turn, create two 1/1 red Warrior creature tokens that are tapped and attacking. Sacrifice them at the beginning of the next end step.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new WarriorToken(), 2, true, true),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new SacrificeEffect(new FilterControlledCreaturePermanent("those tokens"), 2, "those tokens")
                )
        ));
        this.addAbility(ability);
    }

    private DalkovanEncampment(final DalkovanEncampment card) {
        super(card);
    }

    @Override
    public DalkovanEncampment copy() {
        return new DalkovanEncampment(this);
    }
}