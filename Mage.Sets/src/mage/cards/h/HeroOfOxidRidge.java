
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class HeroOfOxidRidge extends CardImpl {

    public HeroOfOxidRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BattleCryAbility());
        this.addAbility(new AttacksTriggeredAbility(new HeroOfOxidRidgeEffect(), false));
    }

    public HeroOfOxidRidge(final HeroOfOxidRidge card) {
        super(card);
    }

    @Override
    public HeroOfOxidRidge copy() {
        return new HeroOfOxidRidge(this);
    }

}

class HeroOfOxidRidgeEffect extends RestrictionEffect {

    public HeroOfOxidRidgeEffect() {
        super(Duration.EndOfTurn);
        staticText = "creatures with power 1 or less can't block this turn";
    }

    public HeroOfOxidRidgeEffect(final HeroOfOxidRidgeEffect effect) {
        super(effect);
    }

    @Override
    public HeroOfOxidRidgeEffect copy() {
        return new HeroOfOxidRidgeEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getPower().getValue() <= 1;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

}
