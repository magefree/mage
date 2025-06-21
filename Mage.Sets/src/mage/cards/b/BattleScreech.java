package mage.cards.b;

import mage.ObjectColor;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.BirdToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BattleScreech extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white creatures you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BattleScreech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Create two 1/1 white Bird creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BirdToken(), 2));

        // Flashback-Tap three untapped white creatures you control.
        this.addAbility(new FlashbackAbility(this, new TapTargetCost(new TargetControlledPermanent(3, filter))));
    }

    private BattleScreech(final BattleScreech card) {
        super(card);
    }

    @Override
    public BattleScreech copy() {
        return new BattleScreech(this);
    }
}
