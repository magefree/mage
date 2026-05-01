package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.Dwarf22Token;

/**
 *
 * @author muz
 */
public final class AnUnexpectedParty extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AnUnexpectedParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.SORCERY}, "{2}{W}{W}", "At the Door", "{X}{2}{W}");

        // As this enchantment enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield, filter, false
        )));

        // At the Door
        // Create X 2/2 red Dwarf creature tokens.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new Dwarf22Token(), GetXValue.instance));
    }

    private AnUnexpectedParty(final AnUnexpectedParty card) {
        super(card);
    }

    @Override
    public AnUnexpectedParty copy() {
        return new AnUnexpectedParty(this);
    }
}
