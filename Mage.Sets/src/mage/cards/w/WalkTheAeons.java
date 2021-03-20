package mage.cards.w;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.turn.AddExtraTurnTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WalkTheAeons extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public WalkTheAeons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Buyback—Sacrifice three Islands. (You may sacrifice three Islands in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)
        this.addAbility(new BuybackAbility(new SacrificeTargetCost(new TargetControlledPermanent(3, filter))));

        // Target player takes an extra turn after this one.
        this.getSpellAbility().addEffect(new AddExtraTurnTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WalkTheAeons(final WalkTheAeons card) {
        super(card);
    }

    @Override
    public WalkTheAeons copy() {
        return new WalkTheAeons(this);
    }
}
