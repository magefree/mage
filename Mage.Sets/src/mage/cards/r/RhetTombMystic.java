package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author sobiech
 */
public final class RhetTombMystic extends CardImpl {

    public RhetTombMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature card in your hand has cycling {1}{U}.
        this.addAbility(new SimpleStaticAbility(new RhetTombMysticEffect()));
    }

    private RhetTombMystic(final RhetTombMystic card) {
        super(card);
    }

    @Override
    public RhetTombMystic copy() {
        return new RhetTombMystic(this);
    }
}

class RhetTombMysticEffect extends ContinuousEffectImpl {

    RhetTombMysticEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature card in your hand has cycling {1}{U}";
    }

    private RhetTombMysticEffect(final RhetTombMysticEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player controller = game.getPlayer(source.getControllerId());
        if (controller == null)
            return false;

        controller.getHand().getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .forEach(card -> game.getState().addOtherAbility(card, new CyclingAbility(new ManaCostsImpl<>("{1}{U}"))));

        return true;
    }

    @Override
    public ContinuousEffect copy() {
        return new RhetTombMysticEffect(this);
    }
}
