package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class AlienAngelToken extends TokenImpl {

    public AlienAngelToken() {
        super("Alien Angel Token", "2/2 black Alien Angel artifact creature token with first strike, vigilance, and \"Whenever an opponent casts a creature spell, this token isn't a creature until end of turn.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.ANGEL);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(FirstStrikeAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
        addAbility(new SpellCastOpponentTriggeredAbility(
                new AlienAngelTokenEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));
    }

    private AlienAngelToken(final AlienAngelToken token) {
        super(token);
    }

    public AlienAngelToken copy() {
        return new AlienAngelToken(this);
    }
}

class AlienAngelTokenEffect extends ContinuousEffectImpl {

    AlienAngelTokenEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.UnboostCreature);
        staticText = "this token isn't a creature until end of turn";
    }

    private AlienAngelTokenEffect(final AlienAngelTokenEffect effect) {
        super(effect);
    }

    @Override
    public AlienAngelTokenEffect copy() {
        return new AlienAngelTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.removeAllSubTypes(game, SubTypeSet.CreatureType);
        permanent.removeCardType(game, CardType.CREATURE);
        return true;
    }
}
