package mage.cards.g;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledArtifactPermanent;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class Glimmervoid extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control no artifacts"), ComparisonType.EQUAL_TO, 0
    );

    public Glimmervoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // At the beginning of the end step, if you control no artifacts, sacrifice Glimmervoid.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false, condition
        ));

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private Glimmervoid(final Glimmervoid card) {
        super(card);
    }

    @Override
    public Glimmervoid copy() {
        return new Glimmervoid(this);
    }
}
