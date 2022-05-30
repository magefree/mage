package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GanaxAstralHunter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragon");

    public GanaxAstralHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ganax, Astral Hunter or another Dragon you control enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), filter, false, true
        ));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private GanaxAstralHunter(final GanaxAstralHunter card) {
        super(card);
    }

    @Override
    public GanaxAstralHunter copy() {
        return new GanaxAstralHunter(this);
    }
}
