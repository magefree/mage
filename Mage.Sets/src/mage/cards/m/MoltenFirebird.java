package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.*;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class MoltenFirebird extends CardImpl {

    public MoltenFirebird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Molten Firebird dies, return it to the battlefield under its owner’s control at the beginning of the next end step and you skip your next draw step.
        Ability ability = new DiesSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect())));
        ability.addEffect(new SkipNextDrawStepControllerEffect());
        this.addAbility(ability);

        // {4}{R}: Exile Molten Firebird.
        this.addAbility(new SimpleActivatedAbility(new ExileSourceEffect(), new ManaCostsImpl<>("{4}{R}")));
    }

    private MoltenFirebird(final MoltenFirebird card) {
        super(card);
    }

    @Override
    public MoltenFirebird copy() {
        return new MoltenFirebird(this);
    }
}
