package mage.cards.w;

import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class WingShards extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("an attacking creature");

    public WingShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");

        // Target player sacrifices an attacking creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Storm
        this.addAbility(new StormAbility());
    }

    private WingShards(final WingShards card) {
        super(card);
    }

    @Override
    public WingShards copy() {
        return new WingShards(this);
    }
}
