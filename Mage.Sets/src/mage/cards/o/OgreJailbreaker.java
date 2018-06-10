
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class OgreJailbreaker extends CardImpl {

    public OgreJailbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Ogre Jailbreaker can attack as though it didn't have defender as long as you control a Gate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OgreJailbreakerEffect()));

    }

    public OgreJailbreaker(final OgreJailbreaker card) {
        super(card);
    }

    @Override
    public OgreJailbreaker copy() {
        return new OgreJailbreaker(this);
    }
}

class OgreJailbreakerEffect extends AsThoughEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    private PermanentsOnTheBattlefieldCondition gateCondition;
    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public OgreJailbreakerEffect() {
        super(AsThoughEffectType.ATTACK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} can attack as though it didn't have defender as long as you control a Gate";
        gateCondition = new PermanentsOnTheBattlefieldCondition(filter);
    }

    public OgreJailbreakerEffect(final OgreJailbreakerEffect effect) {
        super(effect);
        this.gateCondition = effect.gateCondition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OgreJailbreakerEffect copy() {
        return new OgreJailbreakerEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && gateCondition.apply(game, source))  {
            return true;
        }
        return false;
    }

}