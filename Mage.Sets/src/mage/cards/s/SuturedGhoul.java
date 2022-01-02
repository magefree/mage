package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author nantuko
 */
public final class SuturedGhoul extends CardImpl {

    private static final String staticText = "exile any number of creature cards from your graveyard";
    private static final String staticText2 = "Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness";

    public SuturedGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(TrampleAbility.getInstance());

        // As Sutured Ghoul enters the battlefield, exile any number of creature cards from your graveyard.
        this.addAbility(new AsEntersBattlefieldAbility(new SuturedGhoulEffect(), staticText));

        // Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness.
        BoostSourceEffect effect = new BoostSourceEffect(SuturedGhoulPowerCount.instance, SuturedGhoulToughnessCount.instance, Duration.WhileOnBattlefield);
        effect.setText(staticText2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private SuturedGhoul(final SuturedGhoul card) {
        super(card);
    }

    @Override
    public SuturedGhoul copy() {
        return new SuturedGhoul(this);
    }
}

class SuturedGhoulEffect extends OneShotEffect {

    public SuturedGhoulEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of creature cards from your graveyard";
    }

    public SuturedGhoulEffect(SuturedGhoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null || controller == null) {
            return false;
        }
        if (!controller.getGraveyard().isEmpty()) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD);
            if (controller.chooseTarget(Outcome.Benefit, target, source, game)) {
                int count = 0;
                for (UUID uuid : target.getTargets()) {
                    Card card = controller.getGraveyard().get(uuid, game);
                    if (card != null) {
                        card.moveToExile(getId(), permanent.getIdName(), source, game);
                        permanent.imprint(card.getId(), game);
                        count++;
                    }
                }
                Cards cardsToExile = new CardsImpl(target.getTargets());
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);

                String msg = count == 1 ? "1 card" : count + "cards";
                game.informPlayers(permanent.getLogName() + ": " + controller.getLogName() + " exiled " + msg);
            }

        } else {
            game.informPlayers(permanent.getLogName() + ": No cards in graveyard.");
        }
        return true;
    }

    @Override
    public SuturedGhoulEffect copy() {
        return new SuturedGhoulEffect(this);
    }
}

enum SuturedGhoulPowerCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
        if (permanent != null) {
            for (UUID uuid : permanent.getImprinted()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    amount += card.getPower().getValue();
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the total power of the exiled cards";
    }
}

enum SuturedGhoulToughnessCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
        if (permanent != null) {
            for (UUID uuid : permanent.getImprinted()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    amount += card.getToughness().getValue();
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the total toughness of the exiled cards";
    }
}
