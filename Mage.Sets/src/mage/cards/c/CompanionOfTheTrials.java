package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CompanionOfTheTrials extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.GIDEON, "you control a Gideon planeswalker")
    );

    public CompanionOfTheTrials(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{W}: Untap target creature. Activate this ability only if you control a Gideon planeswalker.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new UntapTargetEffect(), new ManaCostsImpl<>("{1}{W}"), condition
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CompanionOfTheTrials(final CompanionOfTheTrials card) {
        super(card);
    }

    @Override
    public CompanionOfTheTrials copy() {
        return new CompanionOfTheTrials(this);
    }
}
