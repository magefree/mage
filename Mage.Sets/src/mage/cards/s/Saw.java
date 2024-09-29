package mage.cards.s;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Saw extends CardImpl {

    public Saw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature attacks, you may sacrifice a permanent other than that creature or Saw. If you do, draw a card.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new SawEffect(), AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private Saw(final Saw card) {
        super(card);
    }

    @Override
    public Saw copy() {
        return new Saw(this);
    }
}

class SawEffect extends OneShotEffect {

    SawEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice a permanent other than that creature or {this}. If you do, draw a card";
    }

    private SawEffect(final SawEffect effect) {
        super(effect);
    }

    @Override
    public SawEffect copy() {
        return new SawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent("another permanent");
        filter.add(AnotherPredicate.instance);
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .map(MageItem::getId)
                .map(PermanentIdPredicate::new)
                .map(Predicates::not)
                .ifPresent(filter::add);
        TargetSacrifice target = new TargetSacrifice(0, 1, filter);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game) && player.drawCards(1, source, game) > 0;
    }
}
// I see, said the blind man
