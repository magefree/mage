package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuturistOperative extends CardImpl {

    public FuturistOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as Futurist Operative is tapped, it's a Human Citizen with base power and toughness 1/1 and can't be blocked.
        Ability ability = new SimpleStaticAbility(new FuturistOperativeEffect());
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), SourceTappedCondition.instance, "and can't be blocked"
        ));
        this.addAbility(ability);

        // {2}{U}: Untap Futurist Operative.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{2}{U}")));
    }

    private FuturistOperative(final FuturistOperative card) {
        super(card);
    }

    @Override
    public FuturistOperative copy() {
        return new FuturistOperative(this);
    }
}

class FuturistOperativeEffect extends ContinuousEffectImpl {

    FuturistOperativeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} is tapped, it's a Human Citizen with base power and toughness 1/1";
    }

    private FuturistOperativeEffect(final FuturistOperativeEffect effect) {
        super(effect);
    }

    @Override
    public FuturistOperativeEffect copy() {
        return new FuturistOperativeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.isTapped()) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.HUMAN, SubType.CITIZEN);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setValue(1);
                    permanent.getToughness().setValue(1);
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
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
