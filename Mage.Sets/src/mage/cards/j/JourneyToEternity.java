package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JourneyToEternity extends TransformingDoubleFacedCard {

    public JourneyToEternity(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "{1}{B}{G}",
                "Atzal, Cave of Eternity",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.getLeftHalfCard().addAbility(ability);

        // When enchanted creature dies, return it to the battlefield under your control, then return Journey to Eternity to the battlefield transformed under your control.
        ability = new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderYourControlAttachedEffect("it"), "enchanted creature");
        ability.addEffect(new JourneyToEternityReturnTransformedSourceEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Atzal, Cave of Eternity
        // <i>(Transforms from Journey to Eternity.)</i>
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Journey to Eternity.)</i>")));

        // {t}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());

        // {3}{B}{G}, {T}: Return target creature card from your graveyard to the battlefield.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{3}{B}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(ability);
    }

    private JourneyToEternity(final JourneyToEternity card) {
        super(card);
    }

    @Override
    public JourneyToEternity copy() {
        return new JourneyToEternity(this);
    }
}

class JourneyToEternityReturnTransformedSourceEffect extends OneShotEffect {

    public JourneyToEternityReturnTransformedSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then return {this} to the battlefield transformed under your control.";
    }

    public JourneyToEternityReturnTransformedSourceEffect(final JourneyToEternityReturnTransformedSourceEffect effect) {
        super(effect);
    }

    @Override
    public JourneyToEternityReturnTransformedSourceEffect copy() {
        return new JourneyToEternityReturnTransformedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            Zone zone = game.getState().getZone(card.getId());
            // cards needs to be in public non battlefield zone
            if (zone == Zone.BATTLEFIELD || !zone.isPublicZone()) {
                return true;
            }
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
        }
        return true;
    }
}
