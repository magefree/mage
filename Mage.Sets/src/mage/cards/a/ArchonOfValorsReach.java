package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfValorsReach extends CardImpl {
    public ArchonOfValorsReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Archon of Valor's Reach enters the battlefield, choose artifact, enchantment, instant, sorcery, or planeswalker.
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseCardTypeEffect(
                        Outcome.Benefit,
                        Arrays.asList(
                                CardType.ARTIFACT,
                                CardType.ENCHANTMENT,
                                CardType.INSTANT,
                                CardType.SORCERY,
                                CardType.PLANESWALKER
                        )).setText("choose artifact, enchantment, instant, sorcery, or planeswalker")
        ));

        // Players can't cast spells of the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchonOfValorsReachReplacementEffect()));
    }

    private ArchonOfValorsReach(final ArchonOfValorsReach card) {
        super(card);
    }

    @Override
    public ArchonOfValorsReach copy() {
        return new ArchonOfValorsReach(this);
    }
}

class ArchonOfValorsReachReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    ArchonOfValorsReachReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells of the chosen type";
    }

    private ArchonOfValorsReachReplacementEffect(final ArchonOfValorsReachReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Object savedType = game.getState().getValue(source.getSourceId() + "_type");
        Card card = game.getCard(event.getSourceId());
        if (savedType instanceof String && card != null) {
            CardType cardType = CardType.fromString((String) savedType);
            return "You can't cast " + cardType.toString() + " spells (" + card.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Object savedType = game.getState().getValue(source.getSourceId() + "_type");
        Card card = game.getCard(event.getSourceId());

        if (savedType instanceof String && card != null) {
            CardType cardType = CardType.fromString((String) savedType);
            if (cardType != null && card.getCardType(game).contains(cardType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArchonOfValorsReachReplacementEffect copy() {
        return new ArchonOfValorsReachReplacementEffect(this);
    }
}
