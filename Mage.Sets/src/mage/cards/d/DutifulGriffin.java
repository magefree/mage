package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DutifulGriffin extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledEnchantmentPermanent("enchantments");

    public DutifulGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{W}, Sacrifice two enchantments: Return Dutiful Griffin from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);
    }

    private DutifulGriffin(final DutifulGriffin card) {
        super(card);
    }

    @Override
    public DutifulGriffin copy() {
        return new DutifulGriffin(this);
    }
}
