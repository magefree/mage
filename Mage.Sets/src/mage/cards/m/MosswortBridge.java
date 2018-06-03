
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class MosswortBridge extends CardImpl {

    public MosswortBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        this.addAbility(new HideawayAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {G}, {T}: You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new HideawayPlayEffect(), new ManaCostsImpl("{G}"), MosswortBridgeTotalPowerCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MosswortBridge(final MosswortBridge card) {
        super(card);
    }

    @Override
    public MosswortBridge copy() {
        return new MosswortBridge(this);
    }
}

enum MosswortBridgeTotalPowerCondition implements Condition {

    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();


    @Override
    public boolean apply(Game game, Ability source) {
        int totalPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            totalPower += permanent.getPower().getValue();
            if (totalPower >= 10) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creatures you control have total power 10 or greater";
    }
}
