
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author spjspj
 */
public final class WallOfForgottenPharaohs extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(SubType.DESERT.getPredicate());
        filterDesertCard.add(SubType.DESERT.getPredicate());
    }

    public WallOfForgottenPharaohs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Wall of Forgotten Pharaohs deals 1 damage to target player. Activate this ability only if you control a Desert or there is a Desert card in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(1),
                new TapSourceCost(),
                new OrCondition(
                        "only if you control a Desert or there is a Desert card in your graveyard",
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveyardCondition(1, filterDesertCard)
                )
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public WallOfForgottenPharaohs(final WallOfForgottenPharaohs card) {
        super(card);
    }

    @Override
    public WallOfForgottenPharaohs copy() {
        return new WallOfForgottenPharaohs(this);
    }
}
