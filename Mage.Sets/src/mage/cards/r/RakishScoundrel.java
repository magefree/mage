package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakishScoundrel extends CardImpl {

    public RakishScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Rakish Scoundrel enters the battlefield or is turned face up, target creature gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Disguise {4}{B/G}{B/G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{B/G}{B/G}")));
    }

    private RakishScoundrel(final RakishScoundrel card) {
        super(card);
    }

    @Override
    public RakishScoundrel copy() {
        return new RakishScoundrel(this);
    }
}
