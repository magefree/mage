package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GiantsAmuletToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class GiantsAmulet extends CardImpl {

    public GiantsAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Giant's Amulet enters the battlefield, you may pay {3}{U}. If you do, create a 4/4 blue Giant Wizard creature token, then attach Giant's Amulet to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GiantsAmuletEffect()));

        // Equipped creature gets +0/+1 and has "This creature has hexproof as long as it's untapped."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                HexproofAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), new InvertCondition(SourceTappedCondition.instance),
                        "{this} has hexproof as long as it's untapped")
                ), AttachmentType.EQUIPMENT));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private GiantsAmulet(final GiantsAmulet card) {
        super(card);
    }

    @Override
    public GiantsAmulet copy() {
        return new GiantsAmulet(this);
    }
}

class GiantsAmuletEffect extends OneShotEffect {

    GiantsAmuletEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {3}{U}. If you do, create a 4/4 blue Giant Wizard creature token, then attach Giant's Amulet to it.";
    }

    GiantsAmuletEffect(final GiantsAmuletEffect effect) {
        super(effect);
    }

    @Override
    public GiantsAmuletEffect copy() {
        return new GiantsAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player != null) {
            if (player.chooseUse(Outcome.BoostCreature, "Do you want to pay {3}{U}?", source, game)) {
                Cost cost = new ManaCostsImpl<>("{3}{U}");
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    CreateTokenEffect effect = new CreateTokenEffect(new GiantsAmuletToken());
                    if (effect.apply(game, source)) {
                        Permanent p = game.getPermanent(effect.getLastAddedTokenId());
                        if (p != null) {
                            p.addAttachment(source.getSourceId(), source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
