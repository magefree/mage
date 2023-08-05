
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SilasRennSeekerAdept extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact card in your graveyard");

    public SilasRennSeekerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Whenever Silas Renn, Seeker Adept deals combat damage to a player, choose target artifact card in your graveyard. You may cast that card this turn.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SilasRennSeekerAdeptPlayEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SilasRennSeekerAdept(final SilasRennSeekerAdept card) {
        super(card);
    }

    @Override
    public SilasRennSeekerAdept copy() {
        return new SilasRennSeekerAdept(this);
    }
}

class SilasRennSeekerAdeptPlayEffect extends AsThoughEffectImpl {

    public SilasRennSeekerAdeptPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "choose target artifact card in your graveyard. You may cast that card this turn";
    }

    public SilasRennSeekerAdeptPlayEffect(final SilasRennSeekerAdeptPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SilasRennSeekerAdeptPlayEffect copy() {
        return new SilasRennSeekerAdeptPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(objectId)
                    && source.isControlledBy(affectedControllerId);
        } else {
            // the target card has changed zone meanwhile, so the effect is no longer needed
            discard();
            return false;
        }
    }
}
