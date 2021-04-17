package mage.cards.d;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrownInTheLoch extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("spell with mana value less than or equal to " +
            "the number of cards in its controller's graveyard");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("creature with mana value less than or equal to " +
            "the number of cards in its controller's graveyard");

    static {
        filter.add(DrownInTheLochPredicate.instance);
        filter2.add(DrownInTheLochPredicate.instance);
    }

    public DrownInTheLoch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Choose one —
        // • Counter target spell with converted mana cost less than or equal to the number of cards in its controller's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // • Destroy target creature with converted mana cost less than or equal to the number of cards in its controller's graveyard.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private DrownInTheLoch(final DrownInTheLoch card) {
        super(card);
    }

    @Override
    public DrownInTheLoch copy() {
        return new DrownInTheLoch(this);
    }
}

enum DrownInTheLochPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        Player player = game.getPlayer(game.getControllerId(input.getId()));
        return player != null && input.getManaValue() <= player.getGraveyard().size();
    }
}