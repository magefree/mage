package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SisterHospitaller extends CardImpl {

    public SisterHospitaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Medicus Ministorum -- When Sister Hospitaller enters the battlefield, return target creature card from your graveyard to the battlefield. You gain life equal to its mana value.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SisterHospitallerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability.withFlavorWord("Medicus Ministorum"));
    }

    private SisterHospitaller(final SisterHospitaller card) {
        super(card);
    }

    @Override
    public SisterHospitaller copy() {
        return new SisterHospitaller(this);
    }
}

class SisterHospitallerEffect extends OneShotEffect {

    SisterHospitallerEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield. " +
                "You gain life equal to its mana value";
    }

    private SisterHospitallerEffect(final SisterHospitallerEffect effect) {
        super(effect);
    }

    @Override
    public SisterHospitallerEffect copy() {
        return new SisterHospitallerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        int mv = card.getManaValue();
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        player.gainLife(mv, game, source);
        return true;
    }
}
