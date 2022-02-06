package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WalkerToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Lucille extends CardImpl {

    public Lucille(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace"));
        this.addAbility(ability);

        // Whenever equipped creature attacks, defending player sacrifices a creature. If they do, you create a Walker token.
        this.addAbility(new AttacksAttachedTriggeredAbility(new LucilleEffect()));

        // Equip {4}
        this.addAbility(new EquipAbility(4, false));
    }

    private Lucille(final Lucille card) {
        super(card);
    }

    @Override
    public Lucille copy() {
        return new Lucille(this);
    }
}

class LucilleEffect extends OneShotEffect {

    LucilleEffect() {
        super(Outcome.Benefit);
        staticText = "defending player sacrifices a creature. If they do, you create a Walker token";
    }

    private LucilleEffect(final LucilleEffect effect) {
        super(effect);
    }

    @Override
    public LucilleEffect copy() {
        return new LucilleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
            return false;
        }
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(equipment.getAttachedTo(), game));
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), player.getId(), game)) {
            return false;
        }
        player.choose(outcome, target, source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent.sacrifice(source, game)
                && new WalkerToken().putOntoBattlefield(1, game, source, source.getControllerId()
        );
    }
}
