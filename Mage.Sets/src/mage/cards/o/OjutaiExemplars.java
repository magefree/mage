package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OjutaiExemplars extends CardImpl {

    public OjutaiExemplars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, choose one - Tap target creature; 
        Ability ability = new SpellCastControllerTriggeredAbility(
                new TapTargetEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetCreaturePermanent());

        // Ojutai Exemplars gain first strike and lifelink until end of turn; 
        Mode mode = new Mode(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("{this} gains first strike"));
        mode.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and lifelink until end of turn"));
        ability.addMode(mode);

        // or Exile Ojutai Exemplars, then return it to the battlefield tapped under its owner's control.
        ability.addMode(new Mode(new OjutaiExemplarsEffect()));

        this.addAbility(ability);
    }

    private OjutaiExemplars(final OjutaiExemplars card) {
        super(card);
    }

    @Override
    public OjutaiExemplars copy() {
        return new OjutaiExemplars(this);
    }
}

class OjutaiExemplarsEffect extends OneShotEffect {

    OjutaiExemplarsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield tapped under its owner's control";
    }

    private OjutaiExemplarsEffect(final OjutaiExemplarsEffect effect) {
        super(effect);
    }

    @Override
    public OjutaiExemplarsEffect copy() {
        return new OjutaiExemplarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}
