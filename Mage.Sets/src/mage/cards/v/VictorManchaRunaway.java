package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.ExileZone;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class VictorManchaRunaway extends CardImpl {

    public VictorManchaRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Victor Mancha enters, exile target card from your graveyard. You may play it for as long as you control Victor Mancha.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addTarget(new TargetCardInYourGraveyard());
        ability.addEffect(new VictorManchaRunawayEffect());
        this.addAbility(ability);
    }

    private VictorManchaRunaway(final VictorManchaRunaway card) {
        super(card);
    }

    @Override
    public VictorManchaRunaway copy() {
        return new VictorManchaRunaway(this);
    }
}

class VictorManchaRunawayEffect extends OneShotEffect {

    VictorManchaRunawayEffect() {
        super(Outcome.Benefit);
        staticText = "You may play it for as long as you control {this}";
    }

    private VictorManchaRunawayEffect(final VictorManchaRunawayEffect effect) {
        super(effect);
    }

    @Override
    public VictorManchaRunawayEffect copy() {
        return new VictorManchaRunawayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new VictorManchaRunawayPlayEffect()
                .setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);
        return true;
    }
}

class VictorManchaRunawayPlayEffect extends PlayFromNotOwnHandZoneTargetEffect {

    VictorManchaRunawayPlayEffect() {
        super(Zone.EXILED, Duration.WhileControlled);
    }

    private VictorManchaRunawayPlayEffect(final VictorManchaRunawayPlayEffect effect) {
        super(effect);
    }

    @Override
    public VictorManchaRunawayPlayEffect copy() {
        return new VictorManchaRunawayPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!super.applies(objectId, affectedAbility, source, game, playerId)) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        Card card = game.getCard(CardUtil.getMainCardId(game, objectId));
        return exileZone != null && card != null && exileZone.contains(card.getMainCard().getId());
    }
}
