package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class MorbiusTheLivingVampire extends CardImpl {

    public MorbiusTheLivingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {U}{B}, Exile this card from your graveyard: Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                new ManaCostsImpl<>("{U}{B}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private MorbiusTheLivingVampire(final MorbiusTheLivingVampire card) {
        super(card);
    }

    @Override
    public MorbiusTheLivingVampire copy() {
        return new MorbiusTheLivingVampire(this);
    }
}
