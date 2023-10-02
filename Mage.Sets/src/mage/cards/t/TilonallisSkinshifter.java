
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author TheElk801
 */
public final class TilonallisSkinshifter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(AttackingPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public TilonallisSkinshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Tilonalli's Skinshifter attacks, it becomes a copy of another target nonlegendary attacking creature until end of turn.
        Ability ability = new AttacksTriggeredAbility(new TilonallisSkinshifterCopyEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TilonallisSkinshifter(final TilonallisSkinshifter card) {
        super(card);
    }

    @Override
    public TilonallisSkinshifter copy() {
        return new TilonallisSkinshifter(this);
    }
}

class TilonallisSkinshifterCopyEffect extends OneShotEffect {

    public TilonallisSkinshifterCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "it becomes a copy of another target nonlegendary attacking creature until end of turn";
    }

    private TilonallisSkinshifterCopyEffect(final TilonallisSkinshifterCopyEffect effect) {
        super(effect);
    }

    @Override
    public TilonallisSkinshifterCopyEffect copy() {
        return new TilonallisSkinshifterCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            return true;
        }
        return false;
    }
}
