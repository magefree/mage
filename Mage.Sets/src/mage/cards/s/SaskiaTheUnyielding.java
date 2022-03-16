package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SaskiaTheUnyielding extends CardImpl {

    public SaskiaTheUnyielding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // As Saskia the Unyielding enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Damage)));
        // Whenever a creature you control deals combat damage to a player, it deals that much damage to the chosen player.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new SaskiaTheUnyieldingEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.NONE, true
        ));
    }

    private SaskiaTheUnyielding(final SaskiaTheUnyielding card) {
        super(card);
    }

    @Override
    public SaskiaTheUnyielding copy() {
        return new SaskiaTheUnyielding(this);
    }
}

class SaskiaTheUnyieldingEffect extends OneShotEffect {

    public SaskiaTheUnyieldingEffect() {
        super(Outcome.Detriment);
        this.staticText = "it deals that much damage to the chosen player";
    }

    public SaskiaTheUnyieldingEffect(final SaskiaTheUnyieldingEffect effect) {
        super(effect);
    }

    @Override
    public SaskiaTheUnyieldingEffect copy() {
        return new SaskiaTheUnyieldingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
            Player player = game.getPlayer(playerId);
            if (player != null && player.canRespond()) {
                Integer damage = (Integer) this.getValue("damage");
                UUID sourceId = (UUID) this.getValue("sourceId");
                if (sourceId != null && damage > 0) {
                    player.damage(damage, sourceId, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
