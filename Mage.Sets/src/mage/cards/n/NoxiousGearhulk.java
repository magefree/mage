
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class NoxiousGearhulk extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public NoxiousGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Noxious Gearhulk enters the battlefield, you may destroy another target creature. If a creature is destroyed this way, you gain life equal to its toughness.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NoxiousGearhulkEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private NoxiousGearhulk(final NoxiousGearhulk card) {
        super(card);
    }

    @Override
    public NoxiousGearhulk copy() {
        return new NoxiousGearhulk(this);
    }
}

class NoxiousGearhulkEffect extends OneShotEffect {

    public NoxiousGearhulkEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "you may destroy another target creature. If a creature is destroyed this way, you gain life equal to its toughness";
    }

    public NoxiousGearhulkEffect(final NoxiousGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public NoxiousGearhulkEffect copy() {
        return new NoxiousGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, final Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creatureToDestroy = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creatureToDestroy != null && player != null) {
            if (player.chooseUse(Outcome.DestroyPermanent, "Destroy creature?", source, game)) {
                if (creatureToDestroy.destroy(source, game, false)) {
                    player.gainLife(creatureToDestroy.getToughness().getValue(), game, source);
                }
            }
        }
        return true;
    }
}
