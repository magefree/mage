
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.EldraziHorrorToken;

/**
 *
 * @author LevelX2
 */
public final class ExtricatorOfFlesh extends CardImpl {

    private static final FilterControlledCreaturePermanent filterNonEldrazi = new FilterControlledCreaturePermanent("non-Eldrazi creature");
    private static final FilterControlledCreaturePermanent filterEldrazi = new FilterControlledCreaturePermanent(SubType.ELDRAZI, "Eldrazi");

    static {
        filterNonEldrazi.add(Predicates.not(SubType.ELDRAZI.getPredicate()));
    }

    public ExtricatorOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Eldrazi you control have vigilance
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filterEldrazi)));

        // {2}, {T}, Sacrifice a non-Eldrazi creature: Create a 3/2 colorless Eldrazi Horror creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziHorrorToken()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filterNonEldrazi));
        this.addAbility(ability);
    }

    private ExtricatorOfFlesh(final ExtricatorOfFlesh card) {
        super(card);
    }

    @Override
    public ExtricatorOfFlesh copy() {
        return new ExtricatorOfFlesh(this);
    }
}
