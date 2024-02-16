package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class LotlethGiant extends CardImpl {

    public LotlethGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Undergrowth — When Lotleth Giant enters the battlefield, it deals 1 damage to target opponent for each creature card in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(new CardsInControllerGraveyardCount(
                        StaticFilters.FILTER_CARD_CREATURE
                ), "it"), false);
        ability.addTarget(new TargetOpponent());
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
        this.addAbility(ability);
    }

    private LotlethGiant(final LotlethGiant card) {
        super(card);
    }

    @Override
    public LotlethGiant copy() {
        return new LotlethGiant(this);
    }
}
