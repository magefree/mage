package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhenArcanumWeaver extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledEnchantmentPermanent("an enchantment");
    private static final FilterCard filter2
            = new FilterEnchantmentCard("enchantment card from your graveyard");

    public GhenArcanumWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {R}{W}{B}, {T}, Sacrifice an enchantment: Return target enchantment card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{R}{W}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private GhenArcanumWeaver(final GhenArcanumWeaver card) {
        super(card);
    }

    @Override
    public GhenArcanumWeaver copy() {
        return new GhenArcanumWeaver(this);
    }
}
