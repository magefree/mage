
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SiegeBehemoth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control");

    public SiegeBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // As long as Siege Behemoth is attacking, for each creature you control, you may have that creature assign its combat damage as though it weren't blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SiegeBehemothEffect()));
    }

    private SiegeBehemoth(final SiegeBehemoth card) {
        super(card);
    }

    @Override
    public SiegeBehemoth copy() {
        return new SiegeBehemoth(this);
    }
}

class SiegeBehemothEffect extends AsThoughEffectImpl {

    public SiegeBehemothEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "As long as {this} is attacking, for each creature you control, you may have that creature assign its combat damage as though it weren't blocked";
    }

    private SiegeBehemothEffect(final SiegeBehemothEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null && sourcePermanent.isAttacking()){
            Player controller = game.getPlayer(source.getControllerId());
            Permanent otherCreature = game.getPermanent(sourceId);
            if (controller != null && otherCreature != null && otherCreature.isControlledBy(controller.getId())){
                return controller.chooseUse(Outcome.Damage, "Have " + otherCreature.getLogName() + " assign damage as though it weren't blocked?", source, game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SiegeBehemothEffect copy() {
        return new SiegeBehemothEffect(this);
    }
}
