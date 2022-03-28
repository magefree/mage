package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class WallOfForgottenPharaohs extends CardImpl {

    public WallOfForgottenPharaohs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Wall of Forgotten Pharaohs deals 1 damage to target player. Activate this ability only if you control a Desert or there is a Desert card in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1),
                new TapSourceCost(), DesertControlledOrGraveyardCondition.instance
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability.addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private WallOfForgottenPharaohs(final WallOfForgottenPharaohs card) {
        super(card);
    }

    @Override
    public WallOfForgottenPharaohs copy() {
        return new WallOfForgottenPharaohs(this);
    }
}
