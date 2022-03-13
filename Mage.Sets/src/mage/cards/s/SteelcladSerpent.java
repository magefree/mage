package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SteelcladSerpent extends CardImpl {

    public SteelcladSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Steelclad Serpent can't attack unless you control another artifact.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SteelcladSerpentEffect()));
    }

    private SteelcladSerpent(final SteelcladSerpent card) {
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
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(AnotherPredicate.instance);
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
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).isEmpty();
        }
        return false;
    }
}