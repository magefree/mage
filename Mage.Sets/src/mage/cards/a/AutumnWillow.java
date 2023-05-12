
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class AutumnWillow extends CardImpl {

    public AutumnWillow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // {G}: Until end of turn, Autumn Willow can be the target of spells and abilities controlled by target player as though it didn't have shroud.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AutumnWillowEffect(), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AutumnWillow(final AutumnWillow card) {
        super(card);
    }

    @Override
    public AutumnWillow copy() {
        return new AutumnWillow(this);
    }
}

class AutumnWillowEffect extends AsThoughEffectImpl {

    public AutumnWillowEffect() {
        super(AsThoughEffectType.SHROUD, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, Autumn Willow can be the target of spells and abilities controlled by target player as though it didn't have shroud";
    }

    public AutumnWillowEffect(final AutumnWillowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AutumnWillowEffect copy() {
        return new AutumnWillowEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!affectedControllerId.equals(source.getFirstTarget())) { return false; }
        Permanent creature = game.getPermanent(sourceId);

        return creature != null &&sourceId.equals(source.getSourceId());
    }
}
