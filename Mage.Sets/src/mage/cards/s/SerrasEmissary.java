package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerrasEmissary extends CardImpl {

    public SerrasEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Serra's Emissary enters the battlefield, choose a card type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCardTypeEffect(Outcome.Benefit)));

        // You and creatures you control have protection from the chosen card type.
        this.addAbility(new SimpleStaticAbility(new SerrasEmissaryEffect()));
    }

    private SerrasEmissary(final SerrasEmissary card) {
        super(card);
    }

    @Override
    public SerrasEmissary copy() {
        return new SerrasEmissary(this);
    }
}

class SerrasEmissaryEffect extends ContinuousEffectImpl {

    SerrasEmissaryEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "you and creatures you control have protection from the chosen card type";
    }

    private SerrasEmissaryEffect(final SerrasEmissaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Object savedType = game.getState().getValue(source.getSourceId() + "_type");
        if (controller == null
                || savedType == null) {
            return false;
        }
        if (savedType instanceof String) {
            CardType cardType = CardType.fromString((String) savedType);
            FilterCard filter = new FilterCard(cardType + "s");
            filter.add(cardType.getPredicate());
            Ability ability = new ProtectionAbility(filter);
            controller.addAbility(ability);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_CREATURE,
                    source.getControllerId(), source, game
            )) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SerrasEmissaryEffect copy() {
        return new SerrasEmissaryEffect(this);
    }
}
