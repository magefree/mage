package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HalsinEmeraldArchdruid extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public HalsinEmeraldArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {1}: Until end of turn, target token you control becomes a green Bear creature with base power and toughness 4/4 in addition to its other types and colors.
        Ability ability = new SimpleActivatedAbility(new HalsinEmeraldArchdruidEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private HalsinEmeraldArchdruid(final HalsinEmeraldArchdruid card) {
        super(card);
    }

    @Override
    public HalsinEmeraldArchdruid copy() {
        return new HalsinEmeraldArchdruid(this);
    }
}

class HalsinEmeraldArchdruidEffect extends ContinuousEffectImpl {

    HalsinEmeraldArchdruidEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target token you control becomes a green Bear creature " +
                "with base power and toughness 4/4 in addition to its other types and colors";
    }

    private HalsinEmeraldArchdruidEffect(final HalsinEmeraldArchdruidEffect effect) {
        super(effect);
    }

    @Override
    public HalsinEmeraldArchdruidEffect copy() {
        return new HalsinEmeraldArchdruidEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addCardType(game, CardType.CREATURE);
                permanent.addSubType(game, SubType.BEAR);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setGreen(true);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(4);
                    permanent.getToughness().setModifiedBaseValue(4);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
