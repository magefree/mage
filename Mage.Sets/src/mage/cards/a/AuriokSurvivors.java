
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class AuriokSurvivors extends CardImpl {
    private static final FilterCard filter = new FilterCard("Equipment card from your graveyard");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public AuriokSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Auriok Survivors enters the battlefield, you may return target Equipment card from your graveyard to the battlefield. If you do, you may attach it to Auriok Survivors.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addEffect(new AuriokSurvivorsEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private AuriokSurvivors(final AuriokSurvivors card) {
        super(card);
    }

    @Override
    public AuriokSurvivors copy() {
        return new AuriokSurvivors(this);
    }
}

class AuriokSurvivorsEffect extends OneShotEffect {
    AuriokSurvivorsEffect() {
        super(Outcome.Neutral);
        staticText = "If you do, you may attach it to {this}";
    }

    private AuriokSurvivorsEffect(final AuriokSurvivorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (p != null && player != null && sourcePermanent != null) {
            if (player.chooseUse(Outcome.Benefit, "Attach " + p.getName() + " to " + sourcePermanent.getName() + '?', source, game)) {
                sourcePermanent.addAttachment(p.getId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public AuriokSurvivorsEffect copy() {
        return new AuriokSurvivorsEffect(this);
    }
}
