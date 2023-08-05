
package mage.cards.h;

/**
 *
 * @author LevelX
 */


import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 * @author LevelX
 */
public final class HeWhoHungers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Spirit");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public HeWhoHungers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        /* {1}, Sacrifice a Spirit: Target opponent reveals their hand. You choose a card from it.
         * That player discards that card. Activate this ability only any time you could cast a sorcery. */
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardCardYouChooseTargetEffect(), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetOpponent());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        //Soulshift 4 (When this creature dies, you may return target Spirit card with converted mana cost 4 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(4));
    }

    private HeWhoHungers(final HeWhoHungers card) {
        super(card);
    }

    @Override
    public HeWhoHungers copy() {
        return new HeWhoHungers(this);
    }

}