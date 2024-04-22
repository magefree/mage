package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LothoCorruptShirriff extends CardImpl {

    public LothoCorruptShirriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a player casts their second spell each turn, you lose 1 life and create a Treasure token.
        Ability ability = new CastSecondSpellTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), TargetController.ANY
        );
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private LothoCorruptShirriff(final LothoCorruptShirriff card) {
        super(card);
    }

    @Override
    public LothoCorruptShirriff copy() {
        return new LothoCorruptShirriff(this);
    }
}
