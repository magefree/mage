package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GitaxianAnatomist extends CardImpl {

    public GitaxianAnatomist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Gitaxian Anatomist enters the battlefield, you may tap it. If you do, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ProliferateEffect(), new TapSourceCost().setText("tap it"))
        ));
    }

    private GitaxianAnatomist(final GitaxianAnatomist card) {
        super(card);
    }

    @Override
    public GitaxianAnatomist copy() {
        return new GitaxianAnatomist(this);
    }
}
