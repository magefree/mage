
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class SteelcladSerpent extends CardImpl {

    public SteelcladSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Steelclad Serpent can't attack unless you control another artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SteelcladSerpentEffect()));
    }

    public SteelcladSerpent(final SteelcladSerpent card) {
        super(card);
    }

    @Override
    public SteelcladSerpent copy() {
        return new SteelcladSerpent(this);
    }
}

class SteelcladSerpentEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another artifact");
    
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new AnotherPredicate());
    }

    public SteelcladSerpentEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control another artifact";
    }

    public SteelcladSerpentEffect(final SteelcladSerpentEffect effect) {
        super(effect);
    }

    @Override
    public SteelcladSerpentEffect copy() {
        return new SteelcladSerpentEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (!game.getBattlefield().getActivePermanents(filter, source.getControllerId(), permanent.getId(), game).isEmpty()) {
                return false;
            }
            return true;
        } 
        return false;
    }
}