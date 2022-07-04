
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class NyxWeaver extends CardImpl {

    public NyxWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // At the beginning of your upkeep, put the top two cards of your library into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(2), TargetController.YOU, false));
        // {1}{B}{G}, Exile Nyx Weaver: Return target card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{1}{B}{G}"));
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private NyxWeaver(final NyxWeaver card) {
        super(card);
    }

    @Override
    public NyxWeaver copy() {
        return new NyxWeaver(this);
    }
}
