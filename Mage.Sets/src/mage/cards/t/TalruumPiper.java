
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author nigelzor
 */
public final class TalruumPiper extends CardImpl {

    public TalruumPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All creatures with flying able to block Talruum Piper do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TalruumPiperEffect()));
    }

    private TalruumPiper(final TalruumPiper card) {
        super(card);
    }

    @Override
    public TalruumPiper copy() {
        return new TalruumPiper(this);
    }
}

class TalruumPiperEffect extends RequirementEffect {

    public TalruumPiperEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "All creatures with flying able to block {this} do so";
    }

    public TalruumPiperEffect(TalruumPiperEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (sourceCreature != null && sourceCreature.isAttacking()) {
            return permanent.getAbilities().contains(FlyingAbility.getInstance())
                    && permanent.canBlock(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public TalruumPiperEffect copy() {
        return new TalruumPiperEffect(this);
    }
}
