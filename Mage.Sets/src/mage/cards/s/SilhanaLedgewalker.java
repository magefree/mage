

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SilhanaLedgewalker extends CardImpl {

    public SilhanaLedgewalker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Hexproof (This creature can't be the target of spells or abilities your opponents control.)
        this.addAbility(HexproofAbility.getInstance());

        // Silhana Ledgewalker can't be blocked except by creatures with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilhanaLedgewalkerEffect()));
    }

    public SilhanaLedgewalker (final SilhanaLedgewalker card) {
        super(card);
    }

    @Override
    public SilhanaLedgewalker copy() {
        return new SilhanaLedgewalker(this);
    }

}

class SilhanaLedgewalkerEffect extends RestrictionEffect {

    public SilhanaLedgewalkerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked except by creatures with flying";
    }

    public SilhanaLedgewalkerEffect(final SilhanaLedgewalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (source.getSourceId().equals(permanent.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().contains(FlyingAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public SilhanaLedgewalkerEffect copy() {
        return new SilhanaLedgewalkerEffect(this);
    }
}