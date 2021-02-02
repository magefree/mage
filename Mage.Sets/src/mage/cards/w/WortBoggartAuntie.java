
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class WortBoggartAuntie extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin card from your graveyard");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public WortBoggartAuntie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FearAbility.getInstance());

        // At the beginning of your upkeep, you may return target Goblin card from your graveyard to your hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private WortBoggartAuntie(final WortBoggartAuntie card) {
        super(card);
    }

    @Override
    public WortBoggartAuntie copy() {
        return new WortBoggartAuntie(this);
    }
}
