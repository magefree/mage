
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DiscardCostCardManaValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MercurialChemister extends CardImpl {

    public MercurialChemister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {U}, {T}: Draw two cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {R}, {T}, Discard a card: Mercurial Chemister deals damage to target creature equal to the discarded card's converted mana cost.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(DiscardCostCardManaValue.instance), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MercurialChemister(final MercurialChemister card) {
        super(card);
    }

    @Override
    public MercurialChemister copy() {
        return new MercurialChemister(this);
    }
}