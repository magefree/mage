
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class MurmuringBosk extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Treefolk card from your hand");

    static {
        filter.add(SubType.TREEFOLK.getPredicate());
    }

    public MurmuringBosk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.FOREST);

        // <i>({tap}: Add {G}.)</i>
        this.addAbility(new GreenManaAbility());
        // As Murmuring Bosk enters the battlefield, you may reveal a Treefolk card from your hand. If you don't, Murmuring Bosk enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Treefolk card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {W} or {B}. Murmuring Bosk deals 1 damage to you.
        Ability ability = new WhiteManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new BlackManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private MurmuringBosk(final MurmuringBosk card) {
        super(card);
    }

    @Override
    public MurmuringBosk copy() {
        return new MurmuringBosk(this);
    }
}
